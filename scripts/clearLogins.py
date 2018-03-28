from pymongo import MongoClient

client = MongoClient('localhost',27017)
db = client.logins.logins

db.delete_many({})