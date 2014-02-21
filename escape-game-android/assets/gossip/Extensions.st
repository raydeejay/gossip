// extensions to existing classes, to be integrated later

// ideally we want real dictionaries (and Associations)
METHOD Dictionary
allKeys
    ^ (allEntries collect: [ :e | e at: 1 ]) asArray
!
METHOD Dictionary
allValues
    ^ (allEntries collect: [ :e | e at: 2 ]) asArray
!

// This has to be fixed in the image also
META Transcript
contents
    contents isNil ifTrue: [ contents :=  
    'Welcome to Gossip alpha!

To load the example game, open a new Workspace
from the Tools menu and type the following:

    SetupRooms setupExample.
    SetupRooms startExample.

Then select the text and click the doIt button :)

To open the Game Browser:

    GameBrowser open.
    
To exit you can use the menu, or close either
the Transcript or the game window.' ].
    ^ contents
!

 