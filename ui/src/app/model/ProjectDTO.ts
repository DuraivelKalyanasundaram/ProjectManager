import { UserDTO } from './UserDTO';

export class ProjectDTO {
    constructor(
        public id: Number,
        public name: string,
        public startDate: string,
        public endDate: string,
        public priority: string,
        public manager: UserDTO
    ) {}
}