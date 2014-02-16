fieldscan
=========

File Duplication Detector and Drive Scanner

by ProgrammerDan (Daniel Boston)
Started February 15, 2014

Overview
========

I've played with this idea for a while. Basically, I need a simple tool
to index a file system and flexibly identify duplicates. Many different
architectures have come and gone from favor in my mind over the past
few years. For this incarnation, I'm attempting a simpler approach.
Ultimately I'll put a web service frontend on this, but to start this
app on invocation takes a folder as a starting point. It expects a
certain amount of configuration, such as a datasource configuration
and the like. Given that configuration is correct, it will begin to navigate
the folder and child folders recursively (not following links). If prior
data on these folders and files exists, it will update the existing data.
If no data is found, it will construct data. Basically, for each file node
it constructs a set of datapoint "features" that can be used to identify
duplicates. These features are flexible based on file type. Once the
analysis is complete (or if I get multi-core support going, simultaneously)
duplicates will be scanned for progressively. 

Anyway, this is an ongoing adventure, one I've started and stopped many times
on my way. Hopefully I'll stick with this one for a bit.

Details
=======

More details will get added as the project progresses.
