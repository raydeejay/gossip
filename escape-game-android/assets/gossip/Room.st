EVAL Class addNewClass: (Object subclass: 'Room' variables: 'javaObject' classVariables: '')
META Room
forJavaObject: aJavaObject
	| newRoom |
	newRoom := self new.
	newRoom javaObject: aJavaObject.
	^ newRoom
!
METHOD Room
javaObject: aJavaObject
	javaObject := aJavaObject
!
METHOD Room
javaObject
	^ javaObject
!
METHOD Room
call: aMethod
	self javaObject invoke: aMethod
!
METHOD Room
call: aMethod with: first
	self javaObject invoke: aMethod with: first asJavaObject
!
METHOD Room
call: aMethod with: first with: second
	self javaObject
	    invoke: aMethod
	    with: first asJavaObject
	    with: second asJavaObject
!
METHOD Room
call: aMethod with: first with: second with: third
	self javaObject
	    invoke: aMethod
	    with: first asJavaObject
	    with: second asJavaObject
	    with: third asJavaObject
!
