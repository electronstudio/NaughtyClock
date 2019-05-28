ant bundle
# Create an initial disk image (32 megs)
FILE=/tmp/naughtyclocktmp.dmg
OUT=Output/naughtyclock.dmg
rm $FILE
rm $OUT
hdiutil create -size 32m -fs HFS+ -volname "Naughty Clock" $FILE
# Mount the disk image

hdiutil attach $FILE

cp -R NaughtyClock.app "/Volumes/Naughty Clock"
 
# Obtain device information
DEVS=$(hdiutil attach $FILE | cut -f 1)
DEV=$(echo $DEVS | cut -f 1 -d ' ')

# Unmount the disk image
hdiutil detach $DEV
# Convert the disk image to read-only
hdiutil convert $FILE -format UDZO -o $OUT
