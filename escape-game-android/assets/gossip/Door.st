EVAL Class addNewClass: (Reactor subclass: 'Door' variables: '' classVariables: '')
METHOD Door
open
	self call: #switchToState with: #open
!
METHOD Door
close
	self call: #switchToState with: #closed
!
METHOD Door
lock
	self call: #switchToState with: #locked
!
METHOD Door
unlock
	self call: #switchToState with: #closed
!
METHOD Door
key: aReactorName
	self call: #lockedWith with: aReactorName
!
METHOD Door
destination: aRoomName
	self call: #destination with: aRoomName
!
METHOD Door
imageForOpen: aTextureName
	self call: #imageForOpen with: aTextureName
!
METHOD Door
imageForClosed: aTextureName
	self call: #imageForClosed with: aTextureName
!
METHOD Door
imageForLocked: aTextureName
	self call: #imageForLocked with: aTextureName
!
