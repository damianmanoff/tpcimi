import web
import pickle
import random

fileObject = open('../netWork.net','r')
net = pickle.load(fileObject)

urls = (
    '/users', 'list_users',
    '/user_mechant/(.*)/(.*)', 'get_user'
)


class list_users:        
    def GET(self):
        output = 'users:[123]';
        return output

class get_user:
    def GET(self, user, mechant):
        print user + " -- " + mechant
        
        return str(net.activate(	[user, mechant])[0])


app = web.application(urls, globals())
if __name__ == "__main__":
    app.run()