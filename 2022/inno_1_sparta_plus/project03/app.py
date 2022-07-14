from flask import Flask, render_template, request, jsonify, redirect, url_for
from pymongo import MongoClient


app = Flask(__name__)

client = MongoClient('mongodb+srv://cluster0.8qrmv.mongodb.net/?retryWrites=true&w=majority', 27017, username="test", password="test1234")
db = client.dbsparta_plus_week3


@app.route('/')
def main():
    return render_template("index.html")


if __name__ == '__main__':
    app.run('0.0.0.0', port=5000, debug=True)