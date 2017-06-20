#!/bin/sh

cd /opt
tar xvf jenkins_ci_demo-1.0.0.tar.gz
ls /usr/local/jenkins_ci_demo||mkdir /usr/local/jenkins_ci_demo
cp -r /opt/jenkins_ci_demo-1.0.0/* /usr/local/jenkins_ci_demo/
# cd /usr/local/jenkins_ci_demo;python setup.py install 
cd /usr/local/jenkins_ci_demo
cp jenkins_ci_demo/index.html /usr/share/httpd/noindex/
