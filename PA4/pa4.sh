#!/bin/bash
# cmps101-pt.u17 grading
# usage: pa4.sh
# (run within your pa4 directory to test your code)

SRCDIR=https://raw.githubusercontent.com/legendddhgf/cmps101-pt.u17.grading/master/pa4
EXE="pa4-check.sh"
SCRIPT=$(mktemp)

curl $SRCDIR/$EXE > $EXE
chmod +x $EXE
./$EXE
rm -f $EXE
