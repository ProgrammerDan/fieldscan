fieldscan
=========

File Duplication Detector and Drive Scanner

by ProgrammerDan (Daniel Boston)
Started February 15, 2014

Overview
========

I've played with this idea for a while. Basically, I need a simple tool
to index a file system and flexibly identify duplicates. 

Many different architectures have come and gone from favor in my mind 
over the past few years. For this incarnation, I'm attempting a 
simpler approach (believe it or not).

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

Although overall I'm focusing on a simple implementation, simple has a
different meaning for me than other people. I'm more interested in doing
things right than quickly, and that causes me to spend some more time
on details others might skip over.

Case in point, I'm building a full-feature DAO framework for this project.
I didn't intend to do so at first, but I've grown to truly love the
eventual simplicity of a DAO framework. I modelled my DAO framework after
a standard GenericDAO template, which has a client-facing set of Interfaces
which is all the main classes utilizes. The Implementations are fully
hidden by a DAO Factory. This lets me pull out and rewrite the backend,
write full-feature tests, and basically do anything I want to the storage
layer without touching any of the implementations. Keeping the DAO separate
from the application logic is just a good idea, and it's so good that 
inspite of my intention to bypass it this time, I just couldn't.

One thing I am avoiding is Spring. I've worked with it recently and while
it is a super powerful framework, I realized that (1) it's too magical,
(2) it's too bloated, and (3) it was keeping me from truly understanding
some of the underlying wireup mechanisms. So this project, instead, is
forcing me to really look at, consider, and carefully curate my library
choices. 

In addition to using DAO, I'm leveraging Hibernate with DBCP using Postgresql
as my (current) database stack. This is to force myself to really get
intimate with Hibernate, and because I want to learn DBCP better. Postgresql
is my go-to database, and I have a feeling eventually this project might
wind up hosted on AWS, which supports Postgresql via RDS, a technology
I'm very interested in for both business and personal reasons.

This is also proving to be a great framework to start using the java.nio
classes, which although they've been around for quite a while now, I've
never had a good reason to truly learn them. What I'm finding is awesome
so far, even if it's a lot to digest compared to the far simpler java.io
classes and how they deal with the file system. I'm regretting skipping
these classes all these years, already.

Ultimately, the majority of the code I'm writing will be all the pieces that
surround the main algorithm, which is very simple (as described above in 
the overview).
The true challenge will be in the NodeProcessors, which I'm trying to make
as flexible as possible, going so far as to leverage a registration approach.
This will allow me to grow my file processing abilities as I have time.
I'm still trying to figure out how exactly to implement deduplication scanning
within this frame, but it'll work out and in the meantime I've got a great
framework for cataloging a filesystem.

Expect more details as I continue.
