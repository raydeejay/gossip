// extensions to existing classes, to be integrated later

// ideally we want real dictionaries (and Associations)
METHOD Dictionary
allKeys
    ^ (allEntries collect: [ :e | e at: 1 ]) asArray
!
METHOD Dictionary
allValues
    ^ (allEntries collect: [ :e | e at: 2 ]) asArray
!

// This is pretty useful
METHOD JavaObject
isNull
    ^ self printString = 'JavaObject<null>'
!

// This has to be fixed in the image also
META Transcript
clear
    contents := ''
!
META Transcript
contents
    contents isNil ifTrue: [ contents :=  
    'Welcome to Gossip alpha!

To execute code in the Workspace, select it and press
the doIt button, or press Ctrl-d.

To load the example game execute the following:
    File fileIn: ''demo/DemoGame.st''. 

Then, once the file is loaded you can execute:
    DemoGame setup.
    DemoGame start.

To open the Game Browser (work in progress):
    GameBrowser open.
    
To exit you can use the menu, or close either
the Transcript or the game window.
' ].
    ^ contents
!
EVAL Transcript reset


// Keystroke support for text panes

METHOD Pane
keystroke: aString action: aBlock
    <134 self aString aBlock>
!

METHOD Workspace
open
    | d p i |
    
    workspaceVariables := List new.
    textPane := Pane textArea.
    textPane setText: ''.
    textPane keystroke: 'control D' action: [ self doIt ].
    textPane keystroke: 'control P' action: [ self printIt ].
    textPane keystroke: 'control I' action: [ self inspectIt ].
    window := Window new title: 'Workspace'. 
    window width: 400 height: 300.
 
    d := Pane title: 'do it' action: [self doIt].
    p := Pane title: 'print it' action: [self printIt].
    i := Pane title: 'inspect it' action: [self inspectIt].

    window setPane: 
    (Pane north: 
        (Pane west: d east: 
            (Pane west: i east: 
                (Pane west: p east: nil)))  center: textPane south: nil).
    window show.
!

// fix for filingOut classes with nils (parentClass, vars...)

METHOD Class
fileDefinitionTo: aFile
    | myParent myVars myClassVars |

    parentClass isNil
        ifTrue: [ myParent := #UndefinedObject ]
        ifFalse: [ myParent := parentClass name ].

    variables isNil
        ifTrue: [ myVars := '' ]
        ifFalse: [ myVars := variables asString ].

    parentClass isNil
        ifTrue: [ myClassVars := '' ]
        ifFalse: [ myClassVars := self class variables asString ].

    aFile write:
        'EVAL Class addNewClass: ( ' ,
        myParent , ' subclass: ' , $' asString , name , $' asString ,
        ' variables: ' , $' asString , myVars , $' asString ,
        ' classVariables: ' ,$' asString , myClassVars , $' asString ,
        ')' , Char newline asString
!
METHOD Class
fileMethodsTo: aFile
    methods do: [ :m | aFile write:
        'METHOD ' , name , Char newline asString ,
        m text , Char newline asString ,
        '!' , Char newline asString ].

    self class methods do: [ :m | aFile write:
        'META ' , name , Char newline asString ,
        m text , Char newline asString ,
        '!' , Char newline asString ].

!
METHOD Class
fileOut
    "File out myself"
    |filename|

    filename := name , '.st'.
    self fileTo: (File openWrite: filename)
!
METHOD Class
fileTo: aFile
    self fileDefinitionTo: aFile.
    self fileMethodsTo: aFile
!

META Transcript
showError: aString
    aString = '' ifTrue:[^self.].
    contents := aString+ '
' + self contents.
    pane setText: contents.
    window repaint.
!

METHOD ClassBrowser2
addDefaultMenu
    | m r |

    m := Menu new: 'Class'.
    m on: 'New subclass of...' do: [
        Class addNewClass: (Class classes at: classPane getIndex) subclass.
        self resetClassPane ].

    m on: 'Rename...' do: [
        r := Window getString: 'New name for ' , (Class classes at: classPane getIndex) name.
        (Class classes at: classPane getIndex) name: r.
        self resetClassPane ].

    m on: 'Delete' do: [
        (Window question: 'Delete ' ,  (Class classes at: classPane getIndex) name , '?')
            ifTrue: [
                Class removeClass: (Class classes at: classPane getIndex).
                self resetClassPane ] ].
    window addMenu: m.

    m := Menu new: 'Method'.
    m on: 'Delete' do: [
        (Class classes at: classPane getIndex) removeMethod:
            ((Class classes at: classPane getIndex) methods at:
                methodPane getIndex) name.
        self methodListFor: classPane getIndex meta: meta list: methodPane ].

    window addMenu: m
!
