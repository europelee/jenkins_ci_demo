#!/usr/bin/env python

import os
from jenkins_ci_demo import __version__
from setuptools import setup
# from distutils.core import setup

f = open(os.path.join(os.path.dirname(__file__), 'README'))
long_description = f.read()
f.close()

setup(name='jenkins_ci_demo',
      version=__version__,
      description='jenksin_ci_demo',
      long_description=long_description,
      author='europelee',
      author_email='europe.lee@gmail.com',
      packages=['jenkins_ci_demo'],
      package_data={'': ['VERSION']},
      include_package_data=True,
      install_requires=[
          "coverage==4.3.1",
          "pytest==3.0.5",
          "pytest-cov==2.4.0",
          "pytest-html==1.13.0"
      ]
      )
