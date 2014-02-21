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

// This has to be fixed in the image also
META Transcript
clear
    contents := ''
!
META Transcript
contents
    contents isNil ifTrue: [ contents :=  
    'Welcome to Gossip alpha!

To load the example game, open a new Workspace
from the Tools menu and type the following:

    SetupRooms setupExample.
    SetupRooms startExample.

Then select the text and click the doIt button :)

To open the Game Browser:

    GameBrowser open.
    
To exit you can use the menu, or close either
the Transcript or the game window.' ].
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
