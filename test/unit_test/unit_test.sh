#!/bin/sh

#Absolute path to this script
SCRIPT=$(readlink -f $0)
#Absolute path this script is in
SCRIPTPATH=$(dirname $SCRIPT)

py.test $SCRIPTPATH/test_jenkins_ci_demo.py --html=$1/test_report/test_result.html
