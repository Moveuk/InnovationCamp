from pymongo import MongoClient
client = MongoClient('mongodb+srv://test:YUlHxMAFAVDwps7X@cluster0.8qrmv.mongodb.net/?retryWrites=true&w=majority')
db = client.mongoTest

# 제목이 가버나움인 영화의 별점 보이기
movie = db.movies.find_one({'title':'가버나움'})
print(movie['star'])

# 가버나움의 별점과 같은 영화 찾기
movies = list(db.movies.find({'star':movie['star']},{'_id':False}))
print(movies)
for m in movies:
    print(m['title'])

# 가버나움의 별점을 문자열 0으로 만들기
db.movies.update_one({'title':'가버나움'},{'$set':{'star':'0'}})

