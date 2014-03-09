// This little hack allows us to refer to GameRegistry class even when its
// methods can't be defined yet, because they reference all the other classes
//EVAL Class addNewClass: (Object subclass: 'GameRegistry' variables: 'reactors rooms inventory currentRoom javaObject' classVariables: 'instance javaObject')

META GameRegistry
instance
    instance isNil ifTrue: [ instance := self new ].
    ^ instance
!
METHOD GameRegistry
initialize
    super initialize.
    reactors := Dictionary new.
    rooms := Dictionary new.
!
METHOD GameRegistry
javaObject
    javaObject isNil ifTrue: [ javaObject := self class javaObject ].
    ^ javaObject
!
METHOD GameRegistry
rooms
    ^ rooms
!
METHOD GameRegistry
reactors
    ^ reactors
!
METHOD GameRegistry
inventory
    inventory isNil
        ifTrue: [ inventory := Inventory forJavaObject: (self javaObject invoke: 'getInventory') ].
    ^ inventory
!

META GameRegistry
javaObject
	javaObject isNil ifTrue: [ javaObject := (System getJavaClass: 'net.raydeejay.escapegame.GameRegistry') invokeStatic: 'instance' ].
	^ javaObject. 
!
META GameRegistry
newReactor: aString
	| aReactor |
	aReactor := (self javaObject invoke: 'newReactor' with: aString asJavaObject).
	^ ((Reactor forJavaObject: aReactor) name: aString) register.
!
META GameRegistry
newRoom: aString
	| aRoom |
	aRoom := (self javaObject invoke: 'newRoom' with: aString asJavaObject).
	^ ((Room forJavaObject: aRoom) name: aString) register.
!


META GameRegistry
newJavaReactor: aString
	^ self javaObject invoke: 'newReactor' with: aString asJavaObject
!
META GameRegistry
newJavaRoom: aString
	^ self javaObject invoke: 'newRoom' with: aString asJavaObject
!


META GameRegistry
newHudElement: aString
	| aReactor |
	aReactor := (self javaObject invoke: 'newReactor' with: aString asJavaObject).
	^ ((HudElement forJavaObject: aReactor) name: aString) register.
!
META GameRegistry
switchToRoom: aString
    self javaObject invoke: #switchToRoom with: aString asJavaObject.
!

METHOD GameRegistry
registerReactor: aReactor
    reactors at: aReactor name put: aReactor.
!
METHOD GameRegistry
registerRoom: aRoom
    rooms at: aRoom name put: aRoom.
!

METHOD GameRegistry
getReactor: aString
	^ reactors at: aString ifAbsent: [ nil ].
!
METHOD GameRegistry
getRoom: aString
	^ rooms at: aString ifAbsent: [ nil ].
!
METHOD GameRegistry
currentRoom
	^ currentRoom
!
METHOD GameRegistry
switchToRoom: aString
    | room |
    
    room := self getRoom: aString.
    currentRoom := room.
    
    (self getReactor: #arrowLeft) setVisible: room left notNil. 
    (self getReactor: #arrowRight) setVisible: room right notNil. 
"    (self getReactor: #arrowUp) setVisible: room up notNil." 
"    (self getReactor: #arrowDown) setVisible: room down notNil."
 
    self javaObject invoke: #switchToRoom with: room name asJavaObject.
!

METHOD GameRegistry
addToInventory: anItem
    anItem room: #SpecialInventoryRoom.
    self javaObject invoke: #addToInventory with: anItem javaObject.
"    inventory add: anItem."
!
METHOD GameRegistry
removeItem: aJavaObject
    self javaObject invoke: #removeItem with: aJavaObject.
"    inventory remove: aJavaObject."
!

METHOD GameRegistry
winGame
    "This is not exactly right, but it avoids having to reference DemoGame here"
    File fileIn: 'demo/WinGame.st'.
!

METHOD GameRegistry
recreateJavaObjects
    System println: 'Recreating rooms'.
    rooms allValues do: [ :e | e recreateJavaObject ].
    System println: 'Recreating reactors'.
    reactors allValues do: [ :e | e recreateJavaObject ].
    System println: 'Recreating inventory?'.
    "inventory do: [ :e | e recreateJavaObject ]."
!
