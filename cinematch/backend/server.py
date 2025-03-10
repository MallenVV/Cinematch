from flask import Flask, request, jsonify ,abort
import uuid
import os
from dotenv import load_dotenv
from flask_bcrypt import Bcrypt
from flask_sqlalchemy import SQLAlchemy
from sqlalchemy.orm import Mapped, mapped_column, relationship
from sqlalchemy import ForeignKey
from flask_jwt_extended import JWTManager, create_access_token, jwt_required, get_jwt
import datetime

app = Flask(__name__)
bcrypt = Bcrypt(app)
load_dotenv()
app.config['JWT_ACCESS_TOKEN_EXPIRES'] = datetime.timedelta(minutes=5)
app.config['JWT_SECRET_KEY'] = os.getenv('JWT_SECRET')
jwt = JWTManager(app)
app.config["SQLALCHEMY_DATABASE_URI"] = "sqlite:///./data.db"
db = SQLAlchemy(app)

class User(db.Model):
    id_user:Mapped[str] = mapped_column(primary_key = True, nullable = False)
    username:Mapped[str] = mapped_column(unique = True, nullable = False)
    user_email:Mapped[str] = mapped_column()
    password:Mapped[str] = mapped_column(nullable = False)
    avatar:Mapped[str] = mapped_column(nullable = False)
    def __init__(self, username, password, email = None):
        self.id_user = str(uuid.uuid4())
        self.username = username
        self.password = bcrypt.generate_password_hash(password).decode('utf-8')
        self.user_email = email


class Messages(db.Model):
    id_message:Mapped[str] = mapped_column(primary_key = True, nullable = False)
    date:Mapped[str] = mapped_column(nullable = False)
    title:Mapped[str] = mapped_column(nullable = False)
    message:Mapped[str] = mapped_column(nullable = False)
    id_parent:Mapped[str] = mapped_column(ForeignKey('messages.id_message'))
    image:Mapped[str] = mapped_column()


class UserMessageRelationMovies(db.Model):
    id_relation:Mapped[str] = mapped_column(primary_key = True, nullable = False)
    id_message:Mapped[str] = mapped_column(ForeignKey('messages.id_message'))
    id_user:Mapped[str] = mapped_column(ForeignKey('user.id_user'))
    id_movie:Mapped[str] = mapped_column(nullable = False)



class UserLikesMovieRelation(db.Model):
    id_relation:Mapped[str] = mapped_column(primary_key = True, nullable = False)
    id_movie:Mapped[str] = mapped_column(nullable = False) # API Id from the movie
    id_user:Mapped[str] = mapped_column(ForeignKey('user.id_user'))


class RatingsMovies(db.Model):
    id_relation:Mapped[str] = mapped_column(primary_key = True, nullable = False)
    id_movie:Mapped[str] = mapped_column(nullable = False) # API Id from the movie
    id_user:Mapped[str] = mapped_column(ForeignKey('user.id_user'))
    rating:Mapped[str] = mapped_column(nullable = False)


class TokenBlocklist(db.Model):
    id_token:Mapped[str] = mapped_column(primary_key = True, nullable = False)


with app.app_context():
    db.create_all()


@app.route("/logout", methods = ["POST"])
@jwt_required()
def logout_user():
    jti = get_jwt()['jti']
    res = add_token_blocklist(jti)['result']
    if res:
        return jsonify({"message": "You have logged out"}), 200
    return jsonify({"message": "couldn't log out"}), 500


@app.route("/register", methods = ["POST"])
def create_user():
    result = request.json
    username = result['username']
    password = result['password']
    email = result['email']
    if username and password:
        result_add_user = add_user_db(username= username, password=password, email=email)
        if result_add_user['result']:
            return jsonify({"username": username, "password": password}), 200
    return jsonify({"message": "user already exists or forgot information"}), 400



@jwt.token_in_blocklist_loader
def check_if_token_is_revoked(jwt_header, jwt_payload:dict):
    jti = jwt_payload['jti']
    return get_token_blocklist(jti)


@app.route("/login", methods = ["POST"])
def login_user():
    user_info = request.json
    username = user_info['username']
    password = user_info['password']

    user = get_user_db(username = username)
    if user['result'] and bcrypt.check_password_hash(user['user'].password, password):
        token = create_access_token(identity=user["user"].id_user)
        return jsonify({"token": token}), 200
    return jsonify({'message': "No such user or wrong password"}), 400


#Can be used by intern systems that has ID and therefore the check exists
def get_user_db(user_id = None, username = None):
    if user_id != None:
        user_obj = db.session.execute(db.select(User).filter_by(id_user = user_id)).scalar_one_or_none()
        if user_obj is None:
            return {"result": False}
        return {"result": True, "user": user_obj }
    
    if username != None:
        user_obj = db.session.execute(db.select(User).filter_by(username = username)).scalar_one_or_none()
        if user_obj is None:
            return {"result": False}
        return {"result": True, "user": user_obj }
    return {"result": False}


def add_user_db(username, password, email = None):
    user_obj = User(username = username, email = email, password = password)
    db.session.add(user_obj)
    db.session.commit()
    return {"result": True}


def add_message_db(message):
    random_id = str(uuid.uuid4())
    message_obj = Messages(id_message = random_id, message = message)
    db.session.add(message_obj)
    db.session.commit()
    return {"result": True, "id": random_id}


def get_token_blocklist(token):
    token_elem = db.session.execute(db.select(TokenBlocklist).filter_by(id_token = token)).scalar_one_or_none()
    return token_elem is not None
    

def add_token_blocklist(token):
    if not get_token_blocklist(token):
        token_obj = TokenBlocklist(id_token = token)
        db.session.add(token_obj)
        db.session.commit()
        return {"result": True}
    return {"result": False}


if __name__ == '__main__':

    app.run(debug=True,port =8090, host = 'localhost')
    




