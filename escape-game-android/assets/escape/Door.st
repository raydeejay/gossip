EVAL Class addNewClass: (Reactor subclass: 'Door' variables: 'key destination imageForOpen imageForClosed imageForLocked' classVariables: '')
METHOD Door
initialize
    | openState closedState lockedState |
    super initialize.

    openState := State new.
    openState name: #open.
    self addState: openState.
     
    closedState := State new.
    closedState name: #closed.
    self addState: closedState.
     
    lockedState := State new.
    lockedState name: #locked.
    self addState: lockedState.

    self installListeners.    
    self switchToState: #closed.
!
METHOD Door
installListeners
    | selectedItem |
    (self getState: #open)
        onClick: [ GameRegistry instance switchToRoom: destination ].
        
    (self getState: #closed)
        onClick: [ self open ].
        
    (self getState: #locked)
        onClick: [ 
            selectedItem := GameRegistry instance getSelectedItem.
            selectedItem notNil
                ifTrue: [
                    (selectedItem name = key)
                        ifTrue: [
                            GameRegistry instance removeItem: selectedItem javaObject. 
    	                    self unlock ] ] ].
!
METHOD Door
key: aReactorName
	key := aReactorName.
!
METHOD Door
destination: aRoomName
	destination := aRoomName.
!
METHOD Door
imageForOpen: aTextureName
	imageForOpen := aTextureName.
!
METHOD Door
imageForClosed: aTextureName
	imageForClosed := aTextureName.
!
METHOD Door
imageForLocked: aTextureName
	imageForLocked := aTextureName.
!
METHOD Door
open
	self switchToState: #open.
	self setImage: imageForOpen.
!
METHOD Door
close
	self switchToState: #closed.
	self setImage: imageForClosed.
!
METHOD Door
lock
        self switchToState: #locked.
        self setImage: imageForLocked.
!
METHOD Door
unlock
	self close.
!

META GameRegistry
newDoor: aString
	| aReactor |
	aReactor := (self javaObject invoke: 'newReactor' with: aString asJavaObject).
	^ ((Door forJavaObject: aReactor) name: aString) register.
!
