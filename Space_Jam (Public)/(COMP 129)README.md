#Overview
Although there is an instructions mode in the title screen, players may still find it challenging to remember the game objectives which can make the game less enjoyable.  In order to solve this problem, I will implement a feature to display relevant instructions on the screen while the game is being played.  Going further, these instructions will be placed in a text file so that others can also edit them in general.

#Pseudocode	
showInstructions()

	if the game started or the player has teleported
		Get the path of the Instructions
		Read the contents of the instructions 
		Store each line in a list
		
		Check the current room
			if room is Earth
				display line 1
			
			else if room is Mercury
				display line 2
				
			else if room is Jupiter
				display line 3
			
			else if room is Neptune 
				display line 4
				
		showIntsruction = False #Will be used to keep in track if player has entered a new room
	return
	
#How to change In-Game Instructions
1. Go to "Instruction.txt" file.
2. In the file, there is an editable lines for every map instruction and for when player collects all 16 keys. 
	For example, if you want to edit the instruction for when player arrives to Neptune, you would edit lines 17-18.
3. Save, Run the Game, and watch your instructions get displayed :)
	