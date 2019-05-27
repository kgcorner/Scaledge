#             .-"""-.
#            / .===. \
#            \/ 6 6 \/
#            ( \___/ )
#  ______ooo__\_____/__________
# /                            \
#| Runs maven with provided jdk |
#|  Author : Kumar Gaurav       |
#| Date: 23-05-2019             |
# \___________________ooo______/
#            |  |  |
#            |_ | _|
#            |  |  |
#            |__|__|
#            /-'Y'-\
#           (__/ \__)

if [ $# != 2 ]
then
    echo "Usage:"
    echo $0 "<Required java home>" "<maven command in quotes>"
    exit -1
fi

CURRENT_JAVA_HOME=`echo $JAVA_HOME`
GIVEN_JAVA_HOME=$1
if [ -d $GIVEN_JAVA_HOME ]
then
    export JAVA_HOME=`echo $GIVEN_JAVA_HOME`
    command=`echo $2`
    echo "Running \"$command\""
    eval $command
else
    echo $GIVEN_JAVA_HOME 'does not exists'
fi
export JAVA_HOME=`echo $CURRENT_JAVA_HOME`

