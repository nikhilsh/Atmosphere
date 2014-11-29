# Update Python path to include libraries on SD card.
import sys
sys.path.append('/mnt/sda1/python-packages')

from flask import Flask, abort, redirect, url_for

if __name__ == '__main__':
    app = Flask(__name__)

    @app.route('/img/<img_id>')
    def fetch(img_id):
        x = url_for('static', filename=img_id + '.png')
        return redirect(x)

    app.run(host='0.0.0.0')
