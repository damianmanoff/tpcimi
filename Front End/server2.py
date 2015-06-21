import web


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
        return str([user, mechant])


app = web.application(urls, globals())
if __name__ == "__main__":
    app.run()