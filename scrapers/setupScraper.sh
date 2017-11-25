VIRTUALENV_NAME="virtualenv"

echo "REMOVING VIRTUAL ENVIRONMENT IFF EXISTS"
rm -rf $VIRTUALENV_NAME

echo "CREATING VIRTUAL ENVIRONMENT"
python3 -mvenv $VIRTUALENV_NAME

echo "SOURCING TO ENVIRONMENT"
source ./$VIRTUALENV_NAME/bin/activate

echo "INSTALLING PIP MODULES"
pip install pip --upgrade
pip install requests
pip install python-dateutil
pip install lxml

echo "RETRIEVING TOP 200 GREATEST ARTISTS"
source $VIRTUALENV_NAME/bin/activate 

