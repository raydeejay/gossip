EVAL Class addNewClass: (Object subclass: 'JavaWrapper' variables: 'javaObject' classVariables: '')
META JavaWrapper
forJavaObject: aJavaObject
	| instance |
	instance := self new.
	instance javaObject: aJavaObject.
	^ instance
!
METHOD JavaWrapper
javaObject: aJavaObject
	javaObject := aJavaObject
!
METHOD JavaWrapper
javaObject
	^ javaObject
!
METHOD JavaWrapper
call: aMethod
	self javaObject invoke: aMethod
!
METHOD JavaWrapper
call: aMethod with: first
	self javaObject invoke: aMethod with: first asJavaObject
!
METHOD JavaWrapper
call: aMethod with: first with: second
	self javaObject
	    invoke: aMethod
	    with: first asJavaObject
	    with: second asJavaObject
!
METHOD JavaWrapper
call: aMethod with: first with: second with: third
	self javaObject
	    invoke: aMethod
	    with: first asJavaObject
	    with: second asJavaObject
	    with: third asJavaObject
!
