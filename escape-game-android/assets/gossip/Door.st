EVAL Class addNewClass: (Reactor subclass: 'Door' variables: 'key destination imageForOpen imageForClosed imageForLocked' classVariables: '')
METHOD Door
initialize
    | openState closedState lockedState gr selectedItem itemName |
    super initialize.
    gr := (System getJavaClass: 'net.raydeejay.escapegame.GameRegistry') invokeStatic: 'instance'.

    openState := State new.
    openState name: #open.
    openState onClick: [ GameRegistry instance switchToRoom: destination. ].
    self addState: openState.
     
    closedState := State new.
    closedState name: #closed.
    closedState onClick: [ self open. ].
    self addState: closedState.
     
    lockedState := State new.
    lockedState name: #locked.
    lockedState onClick: [ 
        selectedItem := gr invoke: #getSelectedItem.
        selectedItem printString = 'JavaObject<null>'
            ifTrue: [ self. "System println: 'nil'" ]
            ifFalse: [
                itemName := (selectedItem invoke: #getName) asSmallString.
    	        itemName = key ifTrue: [
    	            gr invoke: #removeItem with: selectedItem. 
    	            self close. ].
    	    ]. ].
    self addState: lockedState.
    
    self switchToState: #closed.
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
