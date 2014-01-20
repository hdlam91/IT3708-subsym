from kd_array import *
from epuck_basic import *
from imagepro import *
from prims1 import *


controller = EpuckBasic()
controller.basic_setup()
while(True):
	if(get_red(controller.snapshot(), 2,2)==0):
		controller.turn_left()
	if(get_red(controller.snapshot(), 2,2)>0):
		controller.move()
