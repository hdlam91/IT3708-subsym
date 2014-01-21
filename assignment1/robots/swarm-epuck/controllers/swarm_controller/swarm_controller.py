from kd_array import *
from epuck_basic import *
from imagepro import *
from prims1 import *


controller = EpuckBasic()
controller.basic_setup()
while(True):
	controller.move()