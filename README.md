# README #
## Accounts premade ##
* Username:admin Password: admin
* Username: stock Password: stock

## Notes ##
### Admin ###
Passwords for users are created by admin and are then used as login credentials.
### Delete, Move, Copy ###
Deleting, moving, and copying a photo from an album is done with the manage drop-down menu. The drop-down reflects every album the selected photo is in. Checking a new album copies the image to that album and unchecking an album removes the image from that album. To move, as opposed to copy, would require checking the destination album and unchecking the current album. When a photo is unchecked, the photo stays in the photo editing panel until a different photo is selected or added. The photo can still be edited moved or copied as normal. This is to allow the user to rectify a mistake of unchecking the current album by accident. To delete a photo, a user simply must uncheck all the albums and select a different photo.
### Duplicate Photos ###
When uploading a photo, if the photo already exists in another album(based on photo address), the photo is copied to the current album from the other one. If the photo already exists in the current album, it is simply selected. This makes sure that there are no duplicate photos.
### Search Conventions ###
Tags are split by commas and are matched to photos that have the same keywords (also split by commas when updated by user in picture view). Searching for a photo by caption is different from the rest of the tag categories. Photos are found by caption if any keyword (separated by commas) specified by the user are contained in the caption of a photo.

In addition, searching is done with OR logic. Searching for multiple tags within the same field(ie People, Events, Locations, Other) return photos that contain at least one of those tags.

If a search criteria for any tag is empty, photos are not filtered by that tag. For example, searching with no criteria returns all photos.