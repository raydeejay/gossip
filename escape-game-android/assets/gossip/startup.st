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


// Extensions to be integrated later
EVAL File fileInWithNotification: 'gossip/Extensions.st'
EVAL File fileInWithNotification: 'gossip/Delay.st'
EVAL File fileInWithNotification: 'gossip/Schedule.st'
EVAL File fileInWithNotification: 'gossip/PopupMenu.st'
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
EVAL File fileInWithNotification: 'gossip/Room.st'

EVAL File fileInWithNotification: 'gossip/bitfonts/TextLabel.st'
EVAL File fileInWithNotification: 'gossip/bitfonts/ErrorHandlerHelper.st'

EVAL File fileInWithNotification: 'gossip/GameRegistry.st'
EVAL File fileInWithNotification: 'gossip/SetupGossip.st'
EVAL System println: 'Finished loading'


// Authoring tools
EVAL File fileInWithNotification: 'gossip/GameBrowser.st'
EVAL System println: 'Finished loading authoring tools'

// Recreate javaObjects
EVAL GameRegistry instance recreateJavaObjects


// Then start the thing up
EVAL System println: 'Setting things up'
EVAL SetupGossip setup
EVAL System println: 'Starting things up'
EVAL SetupGossip start
EVAL System println: 'Opening Transcript. up'
EVAL Transcript open
