EVAL Class addNewClass: ( Object subclass: 'GameBrowser' variables: 'window roomsPane reactorsPane roomlessPane inventoryPane executeButton executeInvButton executeRoomlessButton refreshButton sourcePane' classVariables: '')
META GameBrowser
open
    ^ self new open
!
METHOD GameBrowser
addRoom
    | r |
    r := (Window getString: 'Name for the new room') asSymbol.
    (GameRegistry newRoom: r).
    self resetRoomsPane
!
METHOD GameBrowser
renameRoom
    | r |
    r := Window getString: 'New name for ' + (GameRegistry instance rooms allValues at: roomsPane getIndex) name.
    "(Class classes at: classPane getIndex) name: r."
    self resetRoomsPane
!
METHOD GameBrowser
deleteRoom
    (Window question: 'Delete ' +  (GameRegistry instance rooms allValues at: roomsPane getIndex) name + '?')
        ifTrue: [ 
            GameRegistry instance deleteRoom: (GameRegistry instance rooms allValues at: roomsPane getIndex) ].
    self resetRoomsPane
!

METHOD GameBrowser
addReactor
    | r |
    r := (Window getString: 'Name for the new reactor') asSymbol.
    (GameRegistry newReactor: r).
    self reactorsListFor: roomsPane getIndex list: reactorsPane
!
METHOD GameBrowser
renameReactor
    | r |
    r := Window getString: 'New name for ' + (GameRegistry instance rooms allValues at: roomsPane getIndex) name.
    "(Class classes at: classPane getIndex) name: r."
    self reactorsListFor: roomsPane getIndex list: reactorsPane
!
METHOD GameBrowser
deleteReactor
    (Window question: 'Delete ' +  (GameRegistry instance rooms allValues at: roomsPane getIndex) name + '?')
        ifTrue: [ 
            GameRegistry instance deleteRoom: (GameRegistry instance rooms allValues at: roomsPane getIndex) ].
        self reactorsListFor: roomsPane getIndex list: reactorsPane
!

METHOD GameBrowser
addDefaultMenu
    | m r |
    
    m := Menu new: 'Game'.
   	m on: 'Name...' do: [ self setGameName ].
    m on: 'Starting room...' do: [ self setStartingRoom ].
    window addMenu: m.

    m := Menu new: 'Room'.
   	m on: 'Edit navigation' do: [ self editNavigation ].
   	m on: 'Add...' do: [ self addRoom ].
    m on: 'Rename...' do: [ self renameRoom ].
    m on: 'Delete' do: [ self deleteRoom ].
    window addMenu: m.

    m := Menu new: 'Reactor'.
   	m on: 'Add...' do: [ self addReactor ].
    m on: 'Rename...' do: [ self renameReactor ].
    m on: 'Delete' do: [ self deleteReactor ].
    window addMenu: m.
!
METHOD GameBrowser
layout
    ^ '(COLUMN 
        (ROW weight=0.55
            (COLUMN weight=0.25
                (LEAF name=rooms weight=0.90)
                (LEAF name=refresh weight=0.10))
            (COLUMN weight=0.25
                (LEAF name=reactors weight=0.90)
                (LEAF name=execute weight=0.10))
            (COLUMN weight=0.25
                (LEAF name=inventory weight=0.90)
                (LEAF name=executeInv weight=0.10))
            (COLUMN weight=0.25
                (LEAF name=roomless weight=0.90)
                (LEAF name=executeRoomless weight=0.10)))
        (LEAF name=source weight=0.45))
    '.
!

METHOD GameBrowser
defaultRoomsPane
    | p rooms |

    rooms := GameRegistry instance rooms.
    p := Pane list: rooms allKeys asStringArray action: [ :i |
        self reactorsListFor: i list: reactorsPane. ].
    ^ p

!
METHOD GameBrowser
resetRoomsPane
    roomsPane setList: GameRegistry instance rooms allKeys asStringArray 
!

METHOD GameBrowser
defaultReactorsPane
    ^ Pane list: #() action: [:i |
        self. "sourcePane setText: ''" ].
!
METHOD GameBrowser
defaultRoomlessPane
    ^ Pane
        list: [ 
            (((GameRegistry instance reactors allValues) 
                select: [ :r | r room isNil ])
                    collect: [ :x | x name ]) asArray ] value
        action: [ :i | self. "sourcePane setText: ''" ].
!
METHOD GameBrowser
defaultInventoryPane
    ^ Pane
        list: [ 
            (((GameRegistry instance reactors allValues) 
                select: [ :r | r room notNil and: [ r room = #SpecialInventoryRoom ] ])
                    collect: [ :x | x name ]) asArray ] value
        action: [ :i | self. "sourcePane setText: ''" ].
!

METHOD GameBrowser
defaultExecuteButton
    | roomName reactorList reactor |
    ^ Pane title: 'execute' action: [
    	"execute the code inside the source pane in the context of the selected reactor"
    	
    	roomName := GameRegistry instance rooms allKeys at: (roomsPane getIndex). 
        reactorList := (GameRegistry instance reactors allValues) select: [ :r | r room = roomName ].  
    	reactor := reactorList at: (reactorsPane getIndex).
    	
    	self monkeyPatch: reactor andEval: sourcePane getText.

    	self refresh ].
!
METHOD GameBrowser
defaultExecuteInvButton
    | reactorList reactor |
    ^ Pane title: 'execute Inventory' action: [
    	"execute the code inside the source pane in the context of the selected inventory item"
    	
        reactorList := (GameRegistry instance reactors allValues) select: [ :r | r room notNil and: [ r room = #SpecialInventoryRoom ] ].
    	reactor := reactorList at: (inventoryPane getIndex).
    	
    	self monkeyPatch: reactor andEval: sourcePane getText.

    	self refresh ].
!
METHOD GameBrowser
defaultExecuteRoomlessButton
    | reactorList reactor |
    ^ Pane title: 'execute Roomless' action: [
    	"execute the code inside the source pane in the context of the selected roomless reactor"
    	
        reactorList := (GameRegistry instance reactors allValues) select: [ :r | r room isNil ].
    	reactor := reactorList at: (roomlessPane getIndex).
    	
    	self monkeyPatch: reactor andEval: sourcePane getText.

    	self refresh ].
!
METHOD GameBrowser
defaultRefreshButton
    ^ Pane title: 'refresh lists' action: [ self refresh ].
!

METHOD GameBrowser
defaultSourcePane
    | t |

    t := Pane textArea.
    t setText: ''.
    ^ t
!

METHOD GameBrowser
monkeyPatch: aReactor andEval: aString
    aReactor class compileMethod: ('XXXdoItXXX ' , aString).
    aReactor doIt.
    	
    "the compilation may fail and this message below may not be sent, this should be fixed"
    aReactor class removeMethod: #XXXdoItXXX.
!
METHOD GameBrowser
refresh
    | reactorList |
    self reactorsListFor: roomsPane getIndex list: reactorsPane.
    	
    reactorList := (GameRegistry instance reactors allValues) select: [ :r | r room notNil and: [ r room = #SpecialInventoryRoom ] ].
    inventoryPane setList: (reactorList collect: [ :x | x name ]) asArray.

    reactorList := (GameRegistry instance reactors allValues) select: [ :r | r room isNil ].
    roomlessPane setList: (reactorList collect: [ :x | x name ]) asArray.
!

METHOD GameBrowser
reactorsListFor: aRoomIndex list: aPane
    | roomName reactorList |
    
    roomName := GameRegistry instance rooms allKeys at: aRoomIndex.
    reactorList := (GameRegistry instance reactors allValues) select: [ :r | r room = roomName ].  
        
    aPane setList: 
        (reactorList collect: [ :x | x name ]) asArray ].
!
METHOD GameBrowser
statesListFor: aReactorIndex list: aPane
    self
!

METHOD GameBrowser
open
    | pane roomName reactor reactorList |

    pane := MultiSplitPane newWithLayout: self layout.

    roomsPane := self defaultRoomsPane.
    pane add: roomsPane at: 'rooms'.

    reactorsPane := self defaultReactorsPane.
    pane add: reactorsPane at: 'reactors'.

    roomlessPane := self defaultRoomlessPane.
    pane add: roomlessPane at: 'roomless'.

    inventoryPane := self defaultInventoryPane.
    pane add: inventoryPane at: 'inventory'.

    refreshButton := self defaultRefreshButton.
    pane add: refreshButton at: 'refresh'.

    executeButton := self defaultExecuteButton.
    pane add: executeButton at: 'execute'.

    executeInvButton := self defaultExecuteInvButton.
    pane add: executeInvButton at: 'executeInv'.

    executeRoomlessButton := self defaultExecuteRoomlessButton.
    pane add: executeRoomlessButton at: 'executeRoomless'.

    sourcePane := self defaultSourcePane.
    pane add: sourcePane at: 'source'.

    window := Window new title: 'Game Browser'.
    window width: 700 height: 500.
    window setPane: pane javaObject.
    self addDefaultMenu.
    window show
!
