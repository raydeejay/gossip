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

// This little hack allows us to refer to GameRegistry class even when its
// methods can't be defined yet, because they reference all the other classes
EVAL Class addNewClass: (Object subclass: 'GameRegistry' variables: 'reactors' classVariables: 'instance javaObject')

// Now load the stuff in the required order
EVAL File fileInWithNotification: 'gossip/JavaWrapper.st'
EVAL File fileInWithNotification: 'gossip/State.st'
EVAL File fileInWithNotification: 'gossip/Reactor.st'
EVAL File fileInWithNotification: 'gossip/Item.st' 
EVAL File fileInWithNotification: 'gossip/Obtainable.st'
EVAL File fileInWithNotification: 'gossip/Dropper.st'
EVAL File fileInWithNotification: 'gossip/Door.st'
EVAL File fileInWithNotification: 'gossip/Room.st'
EVAL File fileInWithNotification: 'gossip/GameRegistry.st'
EVAL File fileInWithNotification: 'gossip/SetupRooms.st'

// Then start the thing up
EVAL SetupRooms setup
EVAL Transcript open
