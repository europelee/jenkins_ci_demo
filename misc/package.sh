#!/bin/sh

#Absolute path to this script
SCRIPT=$(readlink -f $0)
#Absolute path this script is in
SCRIPTPATH=$(dirname $SCRIPT)

ROOT_DIR=$SCRIPTPATH/../
cd $ROOT_DIR
python setup.py sdist
