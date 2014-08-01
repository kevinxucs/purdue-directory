# purdue-directory

A simple Java wrapper for accessing directory system of Purdue University.

It uses Purdue's Purdue Electronic Directory (PED) LDAP server: ped.purdue.edu.

## Example

	Directory directory = new Directory();
	Search search = new Search.Builder().name("kaiwen").build();
	List<ResultEntry> results = directory.search(search);
	directory.close();