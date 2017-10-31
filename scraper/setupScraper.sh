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

echo "RETRIEVING TOP 200 GREATEST ARTISTS"
source $VIRTUALENV_NAME/bin/activate 
curl -Bs http://www.billboard.com/charts/greatest-billboard-200-artists | awk '/chart-row__artist/{getline; print}' | grep -vF "&" | grep -vF "!" | python -c "import sys,json;f = open('artists.json','w+');json.dump([x.strip() for x in sys.stdin.readlines()],f, sort_keys=True, indent=4, separators=(',', ': '));f.close()"

