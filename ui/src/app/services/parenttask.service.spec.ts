import { TestBed } from '@angular/core/testing';

import { ParenttaskService } from './parenttask.service';

describe('ParenttaskService', () => {
  let service: ParenttaskService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ParenttaskService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
