from kd_array import *
from epuck_basic import *
from imagepro import *
from prims1 import *
from search import *
from retrieval import *
from stagnation import *
from copy import deepcopy
import Queue






controller = EpuckBasic()
controller.basic_setup()

previousDist = Queue.Queue(10)
prev1 = 0
#for i in range(0,2):
while(True):
	#controller.move_wheels(500, 500.0, 0.1)
	distances = controller.get_proximities()
	distance_thresh = 200
	light_thresh = 1000

	#stagnation:
	if(previousDist.full()):
		
		prev10 = previousDist.get()
		
		

	if(True):
		#retrieve:
		
		controller.led[8].set(0)
		lights = controller.get_lightValues()
		# swarm_retrieval(lights, light_thresh)

		if(get_retrieval_left_wheel_speed() == 0 and get_retrieval_right_wheel_speed() == 0):
			#search
			# print "print retr"
			distance_thresh = 200
			update_search_speed(controller.get_proximities(), distance_thresh)
			controller.move_wheels(get_search_left_wheel_speed(), get_search_right_wheel_speed(), 0.1)
		#elif(get_retrieval_left_wheel_speed() == 1000 and get_retrieval_right_wheel_speed() == 1000):
		#	valuate_pushing(distances, prev1)
		#	controller.move_wheels(get_retrieval_left_wheel_speed(), get_retrieval_right_wheel_speed(), 0.1)
		#	for i in range(0,8):
		#		controller.led[i].set(1)
		#run retrieval mode
		else:
			controller.move_wheels(get_retrieval_left_wheel_speed(), get_retrieval_right_wheel_speed(), 0.1)
			for i in range(0,8):
				controller.led[i].set(get_LED_state(i))
			
	else: #stagnation:
		controller.led[8].set(1)
		for i in range(0,8):
			controller.led[i].set(0)
		stagnation_recovery(distances, distance_thresh)
		controller.move_wheels(get_stagnation_left_wheel_speed(),get_stagnation_right_wheel_speed(), 0.1)
		#reset_retrieval_wheels()
	if(not previousDist.full()):
		previousDist.put(deepcopy(distances))
	prev1 = deepcopy(distances)
controller.stop_moving()
