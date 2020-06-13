import { UserDTO } from './UserDTO';

export class Project {
    constructor(
        public name: string,
        public startDate: string,
        public endDate: string,
        public priority: string,
        public manager: UserDTO
    ) {}
}