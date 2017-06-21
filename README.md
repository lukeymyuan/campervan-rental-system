Campervan Rental System
===================

A prototype system that serves as the backend of a campervan rental system. Customers can make, change and delete campervan bookings. Campervans can be either Manual or Automatic. A vehicle must be picked up and dropped off at the same depot. Each booking has an ID number and is for one or more campervans for a period of time given by a start date and an end date.

Sample Input
-------------
```
Location Shanghai A1 Automatic
	# Location Shanghai has A1 campervan with automatic transmission
Location Shanghai M1 Manual
Location Shanghai A2 Automatic
Location Sydney A3 Automatic
Location Sydney A4 Automatic
Location Sydney M2 Manual
Request 1 09 Apr 4 11 Apr 4 2 Automatic 1 Manual
	# Assign A1, M1, A2 of Shanghai
	# Output Booking 1 Booking 1 Shanghai A1, M1, A2
Request 2 09 Apr 4 11 Apr 4 2 Automatic 1 Manual
	# Assign A3, A4, M2 of Shanghai
	# Output Booking 1 Booking 1 Shanghai A3, A4, M2
Change 2 09 Apr 4 11 Apr 4 4 Automatic 1 Manual
	# Change cannot be fulfilled
	# Output Change rejected
Print Shanghai
	# Prints all the bookings in Shanghai
```

Sample Output
-------------
```
Booking 1 Shanghai A1, M1, A2
Booking 2 Sydney A3, A4, M2
Change rejected
Shanghai A1 09:00 Apr 04 11:00 Apr 04
Shanghai M1 09:00 Apr 04 11:00 Apr 04
Shanghai A2 09:00 Apr 04 11:00 Apr 04
```
