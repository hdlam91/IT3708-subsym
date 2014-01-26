# /*
#  * stagnation.c Stagnation recovery behavior
#  *
#  * Whenever the e-puck reason about his push behavior not being effective,
#  * the stagnation behavior should trigger. This behavior will reposition the
#  * robot, hopefully getting a spot which will result
#  *  Created on: 23. mars 2011
#  *      Author: jannik
#  */
#include "search.h"
#include <stdlib.h>
#include <stdio.h>

#define TRUE 1
#define FALSE 0
#define NEUTRAL 3
#define ON 1
#define OFF 0
#define IR_DIFF_THRESHOLD 4
#define DISTANCE_DIFF_THRESHOLD 10
#define REVERSE_LIMIT 20
#define TURN_LIMIT 10
#define FORWARD_LIMIT 40
#define NEIGHBOR_LIMIT 300

import random
from search import *
import retrieval
NEUTRAL = 3
ON = 1
OFF = 0
IR_DIFF_THRESHOLD = 4
DISTANCE_DIFF_THRESHOLD =  10
REVERSE_LIMIT  = 20
TURN_LIMIT = 10
FORWARD_LIMIT = 40
NEIGHBOR_LIMIT = 300
REALIGN_LIMIT = 2
#define ALIGN_STRAIGTH_THRESHOLD 10 // If bigger, align straight
#define LOW_DIST_VALUE 10 // if lower (and detecting IR), the sensor is close.

ALIGN_STRAIGTH_THRESHOLD = 10
LOW_DIST_VALUE = 10

#/* Wheel speed variables */
#static double
left_wheel_speed = 0
#static double 
right_wheel_speed = 0

#/* Boolean variables */
has_recovered = False
turn_left = NEUTRAL

#/* Green LED */
green_LED_state = OFF # // Visual feedback

#/* Counters */
reverse_counter = 0
turn_counter = 0
forward_counter = 0
twice = 0
align_counter = 0

# /******************************
#  * Internal functions
# *******************************/

# /* Let it shine baby! */
def LED_blink():
	global green_LED_state
	if(green_LED_state):
		green_LED_state = OFF
	else:
		green_LED_state = ON

def realign(distance_value):
	#// Find the difference of the two front IR sensors
	print "realign"
	dist_diff_front = distance_value[7] - distance_value[0]
	global right_wheel_speed
	global left_wheel_speed
	global has_recovered
	global green_LED_state
	#// Are we pushing straight? If not, maybe we should try. If we are, maybe we should
	#// try pushing from another angle.
	if(abs(dist_diff_front) > ALIGN_STRAIGTH_THRESHOLD):# // True = we are not pushing straight
		#//Lets push straight, but which way are we angled?
		if(distance_value[0] < LOW_DIST_VALUE):# //True = turn little right
			right_wheel_speed = -500
			left_wheel_speed = 500
		elif(distance_value[7] < LOW_DIST_VALUE): # // True = turn little left
			right_wheel_speed = 500
			left_wheel_speed = -500
		elif(distance_value[1] < LOW_DIST_VALUE): # // True = turn right
			right_wheel_speed = -1000
			left_wheel_speed = 700
		elif(distance_value[6] < LOW_DIST_VALUE):# // True = turn left
			right_wheel_speed = 700
			left_wheel_speed = -1000
	#// We are standing straight, lets try pushing with another angle.
	else:
		#// Roll a dice, left angle or right angle?
		ran = random.random()
		if (ran > 0.5):
			right_wheel_speed = -500
			left_wheel_speed = 500
		
		else:
		
			right_wheel_speed = 500
			left_wheel_speed = -500
		
	
	has_recovered = True
	green_LED_state = OFF


# /******************************
#  * External functions
# *******************************/

def find_new_spot(distance_value, DIST_THRESHOLD):
	global has_recovered
	global align_counter
	global reverse_counter
	global turn_counter
	global turn_left
	global turn_right
	global left_wheel_speed
	global right_wheel_speed
	global twice
	global forward_counter
	global green_LED_state
	if(twice == 3): # // Reverse, Turn, Forward, Turn(opposite), Forward.
		reset_stagnation()
		has_recovered = True
		green_LED_state = OFF
		

	elif(reverse_counter != REVERSE_LIMIT):# // Make space by moving away from the box
		reverse_counter = reverse_counter +1
		left_wheel_speed = -500
		right_wheel_speed = -500
	elif(turn_counter != TURN_LIMIT): # // Line up with one of the sides of the box
		turn_counter = turn_counter +1
		forward_counter = 0
		if(turn_left == NEUTRAL):
		#// Roll a dice, left or right?
			ran = random.random()
			if (ran > 0.5):
				turn_left = False
			else:
				turn_left = True

		if(turn_left): # // Turn left
			left_wheel_speed = 0
			right_wheel_speed = 700
		else: # // Turn right
			left_wheel_speed = 700
			right_wheel_speed = 0
	elif(forward_counter != FORWARD_LIMIT):
		forward_counter = forward_counter +1
		if(forward_counter == FORWARD_LIMIT-1):
			twice = twice +1
			print "turning" + str(twice)
			turn_counter = 0
			if(turn_left and twice < 2):
				turn_left = False
			elif(not turn_left and twice < 2):
				turn_left = True
		#update_search_speed(distance_value, DIST_THRESHOLD)
		# left_wheel_speed = get_search_left_wheel_speed()
		# right_wheel_speed = get_search_right_wheel_speed()
		# if((left_wheel_speed > 0) and (right_wheel_speed> 0) ):
		if twice == 2:
			right_wheel_speed = 100
			left_wheel_speed = 100
		else:
			right_wheel_speed = 400
			left_wheel_speed = 400


def reset_stagnation():
	global align_counter
	align_counter = 0
	global has_recovered
	has_recovered = False
	global reverse_counter
	reverse_counter = 0
	global turn_counter
	turn_counter = 0
	global forward_counter
	forward_counter = 0
	global turn_left
	turn_left = NEUTRAL
	global twice
	twice = 0

def stagnation_recovery(distance_sensors_value,  DIST_THRESHOLD):
	global align_counter
	if (align_counter < REALIGN_LIMIT): #// Align
		align_counter = align_counter + 1
		realign(distance_sensors_value)
		print align_counter

	else: #// Reposition
		LED_blink()
		find_new_spot(distance_sensors_value, DIST_THRESHOLD)

# /* To keep pushing or not to keep pushing, that is the question */
def valuate_pushing(dist_value, prev_dist_value):
	#// Only assess this situation once
	#// The front IR sensors pushing against the box
	global has_recovered
	global green_LED_state
	global align_counter
	dist_diff7 = prev_dist_value[7] - dist_value[7]
	dist_diff0 = prev_dist_value[0] - dist_value[0]

	if((abs(dist_diff7)> DISTANCE_DIFF_THRESHOLD) and (abs(dist_diff0)> DISTANCE_DIFF_THRESHOLD)):
		has_recovered = True #// Keep pushing, it is working # no it's not, but oh well
		green_LED_state = OFF #// No more recovery
		# align_counter = 0
		print "push is working0"
	elif((dist_value[5] >NEIGHBOR_LIMIT) and (dist_value[2]>NEIGHBOR_LIMIT)):#{ //Has any neighbors
		has_recovered = True # // Keep pushing, it is working
		green_LED_state = OFF # // No more recovery
		# align_counter = 0
		print "push is working1"
	elif((dist_value[5] >NEIGHBOR_LIMIT) or (dist_value[2]>NEIGHBOR_LIMIT)): #{ //Has any neighbors
		#// Roll a dice, do i trust just one team-mate?
		ran = random.random()
		if (ran > 0.5):
			has_recovered = True #// Keep pushing, it is working
			print "push is working2"
			green_LED_state = OFF #// No more recovery
			# align_counter = 0
		else:
			has_recovered = False
			# print "random < 0.5"
	else:
		has_recovered = False
		print "push is not working"

#/* Return the boolean value of whether or not to continue with this behavior*/
def get_stagnation_state():
	if(has_recovered):
		return False #// Recovered, stagnation behavior done
	return True # // Still processing


#/* Returns the state (ON/OFF) for green LED*/
def get_green_LED_state():
	global green_LED_state
	return green_LED_state


#/* */
def get_stagnation_left_wheel_speed():

	return left_wheel_speed


#/* */
def get_stagnation_right_wheel_speed():

	return right_wheel_speed

def get_close_to_box(dist,dist_threshold):
	if (dist[0] > dist_threshold and dist[7] >dist_threshold):
		return True
	else:
		return False
