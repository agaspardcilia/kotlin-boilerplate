export type Problem = {
  type: string,
  title: string,
  // errors: {Map<string, string?>?} = null, // TODO
  details?: string;
}

export const ProblemDescription = {
  'forbidden-acess': 'TODO',
  'bad-credentials': 'Mail/password conbinaison is incorrect.',
  'user-not-found': 'TODO',
  'mail-already-in-use': 'TODO',
  'user-not-activated': 'TODO',
  'invalid-token': 'TODO',
  'invalid-parameter': 'TODO'
};
