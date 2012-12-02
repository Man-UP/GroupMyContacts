#Code taken from http://blog.carduner.net/2010/05/26/authenticating-with-facebook-on-the-command-line-using-python/
## https://github.com/pythonforfacebook/facebook-sdk required dependencies
#!/usr/bin/python2.6
import os.path
import json
import urllib2
import urllib
import urlparse
import BaseHTTPServer
import webbrowser
from facebook import auth_url, get_access_token_from_code, GraphAPI

APP_ID = '212113275590062'
APP_SECRET = 'c46e82a7fd92912a713963dc8c71d310'
ENDPOINT = 'graph.facebook.com'
REDIRECT_URI = 'http://localhost:8080/'
ACCESS_TOKEN = None
LOCAL_FILE = '.fb_access_token'


class RequestHandler(BaseHTTPServer.BaseHTTPRequestHandler):

    def do_GET(self):

        global ACCESS_TOKEN
        self.send_response(200)
        self.send_header("Content-type", "text/html")
        self.end_headers()

        code = urlparse.parse_qs(urlparse.urlparse(self.path).query).get('code')
        code = code[0] if code else None

        if code is None:
            self.wfile.write("Sorry, authentication failed.")
            sys.exit(1)

	response = get_access_token_from_code(code, REDIRECT_URI, APP_ID, APP_SECRET)

	ACCESS_TOKEN = response['access_token']

        open(LOCAL_FILE,'w').write(ACCESS_TOKEN)
        self.wfile.write("You have successfully logged in to facebook. "
                         "You can close this window now.")


if __name__ == '__main__':
    if not os.path.exists(LOCAL_FILE):
        print "Logging you in to facebook..."
	webbrowser.open(auth_url(APP_ID, REDIRECT_URI, perms=['read_stream']))

	listen_on = urlparse.urlsplit(REDIRECT_URI)
        httpd = BaseHTTPServer.HTTPServer(('127.0.0.1', 8080), RequestHandler)
        while ACCESS_TOKEN is None:
            httpd.handle_request()
    else:
        ACCESS_TOKEN = open(LOCAL_FILE).read()


client = GraphAPI(ACCESS_TOKEN)
print client.request('me/permissions')



