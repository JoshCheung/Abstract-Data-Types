#!/bin/bash
# cmps101-pt.u17 grading
# usage: pa3.sh
# (run within your pa3 directory to test your code)

SRCDIR=https://raw.githubusercontent.com/legendddhgf/cmps101-pt.u17.grading/master/pa3
EXE="pa3-check.sh"
SCRIPT=$(mktemp)

curl $SRCDIR/$EXE > $EXE
chmod +x $EXE
./$EXE
rm -f $EXE
