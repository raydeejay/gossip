////////////////////////////////////////////////////////////
// Small application to generate graph displayed by graphgear.swf
// http://www.creativesynthesis.net/recycling/graphgeardemo/
// Author: Alexandre Bergel (alexandre@bergel.eu)
////////////////////////////////////////////////////////////
EVAL Class addNewClass: ( Object subclass: 'GGXMLGenerator' variables: 'fileStream title indent ' classVariables: '')
METHOD GGXMLGenerator
createSpecFileNamed: aFileName
	self assert: [aFileName isString].
	
	fileStream := File openWrite: aFileName.
	self writeHeader.
	self writeNodesDescription.
	self writeEdgesDescription.
	self writeFooter.
	"fileStream close"
!
METHOD GGXMLGenerator
decrIndent
	indent := indent - 1.
	
!
METHOD GGXMLGenerator
edges
	"this method has to be refined in subclasses"
	"should be a collection of array. Each element array has 
	to follow this pattern: { index1 . index2 [. color [. label]]},
	where [...] is optional"
	^ #()
!
METHOD GGXMLGenerator
incrIndent
	indent := indent + 1.
	
!
METHOD GGXMLGenerator
initialize
	super initialize.
	indent := 0.
	title := self class name.
!
METHOD GGXMLGenerator
nodes
	"this method has to be refined in subclasses"
	^ #()
!
METHOD GGXMLGenerator
write: str
	self writeLine: str
!
METHOD GGXMLGenerator
writeEdgesDescription
	
	| edge es s |
	es := self edges.
	s := es size.
	1 to: s do: [:i|
		edge := es at: i.
		<117 (i printString, '/', self edges size printString)>.
		self write: '<edge sourceNode="n', (edge at: 1) printString, '" targetNode="n', (edge at: 2) printString, '" label="" textcolor="0x000055"/>'.
	]
!
METHOD GGXMLGenerator
writeFooter
	self decrIndent.
	self write: '</graph>'
!
METHOD GGXMLGenerator
writeHeader
	self write: '<?xml version="1.0"?>'.
	self write: '<graph title="', title,'" bgcolor="0xffffff" linecolor="0xcccccc">'.
	self incrIndent.
!
METHOD GGXMLGenerator
writeLine: str
	self assert: [str isString].
	0 to: indent do: [:i|
		fileStream write: '    '].
	fileStream write: str.
	self writeNewLine
!
METHOD GGXMLGenerator
writeNewLine
	fileStream write: (Char new: 10) asString
!
METHOD GGXMLGenerator
writeNodesDescription
	
	| node |
	1 to: self nodes size do: [:i|
		node := self nodes at: i.
"		<117 (i printString, '/', self nodes size printString)>."
		self write: '<node id="n', i printString, '" text="', node printString, '" color="0x6000ff" textcolor="0xffffff"/>'.
	]
!
EVAL Class addNewClass: ( GGXMLGenerator subclass: 'GGSimpleGenerator' variables: '' classVariables: '')
METHOD GGSimpleGenerator
edges
	| a | 
	a := List new.
	a add: #(1 2).
	a add: #(2 3).
	a add: #(3 4).
	a add: #(4 5).
	a add: #(5 6).
	a add: #(6 7).
	a add: #(7 8).
	a add: #(2 5).
	a add: #(2 7).
	^ a asArray
!
METHOD GGSimpleGenerator
nodes
	| a |
	a := Array new: 8.
	a at: 1 put: 'Athena Smalltalk'.
	a at: 2 put: 'is'.
	a at: 3 put: 'cool'.
	a at: 4 put: 'and'.
	a at: 5 put: 'fun'.
	a at: 6 put: 'to'.
	a at: 7 put: 'play'.
	a at: 8 put: 'with'.
	^ a asArray
!
EVAL Class addNewClass: ( GGXMLGenerator subclass: 'GGSqueakKernelGenerator' variables: 'nodes edges ' classVariables: '')
METHOD GGSqueakKernelGenerator
edges
	| ans currentNode t |
	edges isNil ifFalse: [^ edges].
	
	ans := List new.
	1 to: self nodes size do: [:i|
		currentNode := self nodes at: i.
		(currentNode subclasses reject: [:n| (self nodes includes: n) not])
			do: [:subclass| t := Array new: 2.
							t at: 1 put: i.
							t at: 2 put: (self nodes indexOf: subclass ifAbsent: [#notused]).
							ans add: t]
	].
	edges := ans.
	^ ans

!
METHOD GGSqueakKernelGenerator
nodes
	nodes isNil ifFalse: [^ nodes].
	nodes := Class classes asList asArray.
	^ nodes
!

