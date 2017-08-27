# IWasFramed-In
    
## What is it? 
    
I wanted a tool for my tracing table. The idea was - I project images onto the glass surface of my table and trace them (pen and tracing paper). Sometimes I don't want images to be projected, though, I just want a backlight. 'I Was Framed' is made for both of those things.
    
'I Was Framed - In' is a sibling of [this guy over here](https://github.com/comeonism/IWasFramed-Out/) (I Was Framed - Out). 
    
To make it work for your S3 bucket make sure to provide a proper bucket name in Utils.
    
Then, if you run it - it goes off to the S3 bucket and fetches everything you have there, all the jpgs, pngs, spare change, keys. Just the names, though, don't worry. Then it checks if any of the directories it grabbed are already stored on your local drive. The ones that are - get dumped (just like me before prom), the ones that are not - get put in the Images directory. All of this happens when you launch the tool. NB: You can either upload your images to S3 manually, or use 'I Was Framed - Out' to do it for you.
    
Then you get displayed a list of directories that have been fetched. You can navigate the list by using *up* and *down* arrows. 

If you don't feel like tracing and want to use your projector as a backlight instead - just press *left* from the directory picking screen, that will make your screen go white, pressing *escape* in the white screen mode exits the tool. Pressing *right* takes you back to the directories view. 

Pressing *delete* in the directories view will delete the selected directory both locally and on S3. 

Pressing *right* arrow in the directories view will take you into the images view, where you can browse through the images in the directory by using *left* and *right* arrows. Pressing *delete* will delete the current image locally (but not on S3). To get back to the directory selection view - press *up arrow* or *escape*.

Now, you might be wondering: 'Aleks, why the shitty controls?', because the plan is to use custom buttons, so I don't particularly care what function is mapped to which button.
        
     
## Why 'I Was Framed - In'
    
'I Was Framed' because I love awful puns and this tool works with video frames (well, supposed to). 
'In' because there's also an 'Out' and I couldn't figure out a better way to distinguish them from one another. 
    
    
## Technologies used

- ~JavaFX~ Java Swing for GUI stuff (because Raspbian doesn't seem to want to play along with JavaFX);
- Amazon AWS stuff for fetching and deleting things from S3.
