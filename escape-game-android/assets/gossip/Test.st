////////////////////////////////////////////////////////////
// Syntax tests
//
// Author: Sergi Reyner
//
// (also figure out how to alter/tweak/replace the syntax)
////////////////////////////////////////////////////////////

// To have some kind of idea of the loading progress
META File
fileInWithNotification: aFileName
    System println: ('Loading ' , aFileName).
    self fileIn: aFileName'.
!

// This has to be fixed in the image also
META Transcript
contents
    ^ 'Welcome to Gossip alpha!

To load the example game, open a new Workspace
from the Tools menu and type the following:

    SetupRooms setupExample.
    SetupRooms startExample.

Then select the text and click the doIt button :)

To open the Game Browser:

    GameBrowser open.
    
To exit you can use the menu, or close either
the Transcript or the game window.'
!

// Extensions to be integrated later
EVAL File fileInWithNotification: 'gossip/Extensions.st'
EVAL System println: 'Finished loading class extensions'


// This little hack allows us to refer to GameRegistry class even when its
// methods can't be defined yet, because they reference all the other classes
EVAL Class addNewClass: (Object subclass: 'GameRegistry' variables: 'reactors rooms inventory currentRoom javaObject' classVariables: 'instance javaObject')

// Now load the stuff in the required order
EVAL File fileInWithNotification: 'gossip/JavaWrapper.st'
EVAL File fileInWithNotification: 'gossip/State.st'
EVAL File fileInWithNotification: 'gossip/Reactor.st'
EVAL File fileInWithNotification: 'gossip/HudElement.st'
EVAL File fileInWithNotification: 'gossip/Inventory.st'
EVAL File fileInWithNotification: 'gossip/Item.st' 
EVAL File fileInWithNotification: 'gossip/Obtainable.st'
EVAL File fileInWithNotification: 'gossip/Dropper.st'
EVAL File fileInWithNotification: 'gossip/Door.st'
EVAL File fileInWithNotification: 'gossip/Room.st'
EVAL File fileInWithNotification: 'gossip/GameRegistry.st'
EVAL File fileInWithNotification: 'gossip/SetupRooms.st'
EVAL System println: 'Finished loading'

// Authoring tools
EVAL File fileInWithNotification: 'gossip/GameBrowser.st'
EVAL System println: 'Finished loading authoring tools'

// Then start the thing up
EVAL System println: 'Setting things up'

//EVAL SetupRooms setupExample
EVAL SetupRooms setup

//EVAL SetupRooms startExample
EVAL SetupRooms start

EVAL Transcript open

