from flask import Flask, render_template, request, jsonify
from pymongo import MongoClient
client = MongoClient('mongodb+srv://test:test1234@cluster0.8qrmv.mongodb.net/?retryWrites=true&w=majority')
db = client.mongoTest

app = Flask(__name__)

@app.route('/')
def home():
    return render_template('index.html')

@app.route("/homework", methods=["POST"])
def homework_post():
    nickname_receive = request.form['nickname_give']
    comment_receive = request.form['comment_give']

    doc = {
        'nickname':nickname_receive,
        'comment':comment_receive
    }
    db.fanboards.insert_one(doc)
    return jsonify({'msg': '저장 완료!'})

@app.route("/homework", methods=["GET"])
def homework_get():
    message_list = list(db.fanboards.find({},{'_id':False}))
    return jsonify({'messages': message_list})

if __name__ == '__main__':
    app.run('0.0.0.0', port=5000, debug=True)
