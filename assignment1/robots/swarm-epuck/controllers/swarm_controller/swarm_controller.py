from kd_array import *
from epuck_basic import *
from imagepro import *
from prims1 import *
from search import *
from retrieval import *
from stagnation import *
from copy import deepcopy






controller = EpuckBasic()
controller.basic_setup()

previousDist = [0]

#for i in range(0,2):
while(True):
	distances = controller.get_proximities()
	distance_thresh = 200
	light_thresh = 1000

	#stagnation:
	if(len(previousDist)>2):
		reset_stagnation()
		valuate_pushing(distances, previousDist)
		

	if(True):
		#retrieve:
		controller.led[8].set(0)
		lights = controller.get_lightValues()
		swarm_retrieval(lights, light_thresh)

		if(get_retrieval_left_wheel_speed() == 0 and get_retrieval_right_wheel_speed() == 0):
			#search
			#print "print retr"
			distance_thresh = 200
			update_search_speed(controller.get_proximities(), distance_thresh)
			controller.move_wheels(get_search_left_wheel_speed(), get_search_right_wheel_speed(), 0.1)
			
		else:#run retrieval mode
			controller.move_wheels(get_retrieval_left_wheel_speed(), get_retrieval_right_wheel_speed(), 0.1)
			#ontroller.led[8].set(1)
	else:
		controller.led[8].set(1)
		stagnation_recovery(distances, distance_thresh)
		controller.move_wheels(get_stagnation_left_wheel_speed(),get_stagnation_right_wheel_speed(), 0.1)
	previousDist = deepcopy(distances)
controller.stop_moving()
