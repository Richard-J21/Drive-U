export interface DriverRequestDto {
    userId?: number;
    driverId?: number;
    tripDate?: Date;
    timeSlot?: string;
    pickupLocation?: string;
    dropLocation?: string;
    estimatedDuration?: string;
    comments?: string;
}