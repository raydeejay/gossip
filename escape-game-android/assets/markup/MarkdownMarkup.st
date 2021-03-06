EVAL Class addNewClass: (Markup subclass: 'MarkdownMarkup' variables: '' classVariables: '')

COMMENT MarkdownMarkup
I am a Markdown generator.

I generate Github flavour markdown.

Note that nested and numbered lists are quite peculiar
in Markdown, and not properly supported yet.
!

METHOD MarkdownMarkup
render
    ^ contents
!

META MarkdownMarkup
fileExtension
    ^ '.md'
!

META MarkdownMarkup
linkExtension
    ^ ''
!

METHOD MarkdownMarkup
begin
    "Implemented for completeness"
    contents := contents , ''
!

METHOD MarkdownMarkup
end
    "Implemented for completeness"
    contents := contents , ''
!

METHOD MarkdownMarkup
newline
    "Markdown supports newlines"
    contents := contents , Char newline asString
!

METHOD MarkdownMarkup
tab
    "Markdown supports tabs"
    contents := contents , (Char new: 9)
!

METHOD MarkdownMarkup
heading: aString level: aNumber
    | prefix |

    prefix := ''.
    (Interval from: 1 to: aNumber by: 1) do: [ :i | 
        prefix := prefix , '#' ].
    
    contents := contents , prefix , aString , Char newline asString
!

METHOD MarkdownMarkup
link: aString to: aURL
    contents := contents , '[' , aString , '](' , aURL , ')'
!

METHOD MarkdownMarkup
code: aString
    contents := contents , '```' , Char newline asString ,
    aString , Char newline asString ,
    '```' , Char newline asString
!

METHOD MarkdownMarkup
blockQuote: aString
    (aString break: Char newline asString) do: [ :line |
        contents := contents , '> ' , line , Char newline asString ]
!

METHOD MarkdownMarkup
italic: aString
    contents := contents , '_' , aString , '_'
!

METHOD MarkdownMarkup
bold: aString
    contents := contents , '**' , aString , '**'
!

METHOD MarkdownMarkup
underline: aString
    "Markdown doesn't support underline"
    contents := contents , aString
!

METHOD MarkdownMarkup
bar
    "A single _ would suffice, but the extra visibility is worth the space"
    contents := '____' , Char newline asString
!

METHOD MarkdownMarkup
image: aURL
    contents := '<img src="' , aURL , '" />'
!

METHOD MarkdownMarkup
list: aBlock
    aBlock value
!

METHOD MarkdownMarkup
orderedList: aBlock
    self orderedList: aBlock start: 1 
!

METHOD MarkdownMarkup
orderedList: aBlock start: aNumber
    aBlock value
!

METHOD MarkdownMarkup
item: aString
    contents := contents , '*' , aString , Char newline asString
!
