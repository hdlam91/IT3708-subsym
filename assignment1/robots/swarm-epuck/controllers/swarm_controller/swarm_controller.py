from kd_array import *
from epuck_basic import *
from imagepro import *
from prims1 import *


controller = EpuckBasic()
controller.basic_setup()
while(True):
	if(get_red(controller.snapshot(), 2,2)>100):
		controller.turn_left()
	elif(get_red(controller.snapshot(), 2,2)>=100):
		controller.move()
	print get_red(controller.snapshot(), 2,2)
