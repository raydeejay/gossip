////////////////////////////////////////////////////////////
// Syntax tests
//
// Author: Sergi Reyner
//
// (also figure out how to alter/tweak/replace the syntax)
////////////////////////////////////////////////////////////
EVAL File fileIn: 'gossip/Reactor.st'
EVAL File fileIn: 'gossip/Room.st'
EVAL File fileIn: 'gossip/GameRegistry.st'
EVAL File fileIn: 'gossip/Room01.st'
EVAL Room01 setup
EVAL Transcript open
EVAL Workspace open

// setting a callback when a reactor is clicked
// (GameRegistry getReactor: #key) onClick: [
//     (GameRegistry getReactor: #door02)
//         call: #switchToState
//         with: #locked.
//     (GameRegistry getReactor: #key) call: #moveToInventory ]
    