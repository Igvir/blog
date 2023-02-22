---
title: Using Hash Codes as Unique Identifiers
subtitle: A common use case illustrated with filenames
slug: using-hashcodes-as-id
tags: java, hash, hashcode, id, checksum, uid
cover: https://cdn.hashnode.com/res/hashnode/image/upload/v1677083896173/aHYhj1QrG.jpg?auto=compress
domain: blog.igvir.com
---

## Introduction 

In this article, we will cover a common use case for software developers: Using and generating Unique Identifiers.

Let´s start with a definition: 
What is an identifier? The simplest explanation usually [comes from Wikipedia](https://en.wikipedia.org/wiki/Identifier):
>An identifier is a name that identifies (that is, labels the identity of) either a unique object or a unique class of objects, where the "object" or class may be an idea, physical countable object (or class thereof), or physical noncountable substance (or class thereof). The abbreviation ID often refers to identity, identification (the process of identifying), or an identifier (that is, an instance of identification). An identifier may be a word, number, letter, symbol, or any combination of those.

Unique identifiers (UIDs), or identifiers, can be a string value or an integer that we use to address unique resources in a domain. Consumers (Systems or Users that need to refer to that resource) then use these identifiers to fetch the resource from a collection of resources. Without a unique identifier, the simple task of separating the resources and invoking those resources is almost impossible.

We use Identifiers to refer to database structural elements, like the names of tables, files in a collection, or elements in a set.
Another important concept here is [Metadata](https://en.wikipedia.org/wiki/Metadata). And again from Wikipedia:
>Metadata is "data that provides information about other data", but not the content of the data, such as the text of a message or the image itself.
As we will see in this article, metadata and identifiers can be related for different purposes, including generating a Unique Identifier (UID).


## Use Metadata to create Unique Identifiers 

The metadata can be generated using algorithms, an example of this is a Hashing algorithm. Hashing consists of converting a general string of information into metadata that represent it. This is done to scramble the data so that it completely transforms the original value, making the hashed value utterly different from the original while also describing it in a certain way, to the point of identifying it. 
[Hans Peter Luhn](https://en.wikipedia.org/wiki/Hans_Peter_Luhn) invented Hashing. He was a German researcher and scientist who worked at IBM. During his professional life, he created over 80 patents in the field of Computer Science and Information Science.

Hashing is used for various functions, but here we are especially interested in its ability to verify the integrity of an object without having to recognize its content. Two files can be compared for equality easily through hashing. There is no need to open the two documents individually. Hashing compares them byte-by-byte and the computed hash value instantly tells if they are distinct.

### Are Hash Codes Unique Identifiers?

A hash code is of a fixed length, so from a mathematical point of view it cannot be unique for all possible inputs. But all such hash functions are carefully designed to minimize the probability of a collision (two distinct files with the same hash value).

[Scott Chacon and Ben Straub in "Pro Git" book](https://git-scm.com/book/en/v2/Git-Tools-Revision-Selection) talk about Hashing collision options using SHA-1 as UID:

>If all 6.5 billion humans on Earth were programming, and every second, each one was producing code that was the equivalent of the entire Linux kernel history (6.5 million Git objects) and pushing it into one enormous Git repository, it would take roughly 2 years until that repository contained enough objects to have a 50% probability of a single SHA-1 object collision.

Therefore we can say that in most practical cases it is possible to use Hashing algorithms to generate Unique Identifiers.

## Using Hashing to Create a Unique File Names

A simple use case where a Unique Identifier is needed is the naming process for an [Object Storage](https://en.wikipedia.org/wiki/Object_storage). For this example, we assume that objects are immutable so if the object changes a new hashing Identifier will be associated with the new version. The idea is simple we will create a Hash code for every object and use it as a Unique Identifier of that object version.

## Prerequisites

We use Java for our sample code but it could be easily ported to other programming languages. To run the file you will need to install a Java SDK. If you need it, you can see [How to download and install JDK](https://openjdk.org/install/).

## Hands-on with Code

The code could be separated into three steps:
1. Select the algorithm
2. Parse file(s)
3. Generate the UID
   
### Step 1: The Algorithm

There are three main options when you think about the Hashing algorithm you want to use: MD5, SHA-1, or SHA-256. A Hash code is fixed length, MD5 generates a 128 bits code, SHA-1 is 160 bits and SHA-256 is 256 bits. Longer numbers minimize the likelihood of a collision of generated codes.

Federal Information Processing Standards define Secure Hash Standard (SHS) [FIPS PUB 180-4](https://csrc.nist.gov/publications/detail/fips/180/4/final) names seven algorithms: SHA-1, SHA-224, SHA-256,
SHA-384, SHA-512, SHA-512/224, and SHA-512/256.

In 2017, researchers were able to produce two different documents with the same SHA-1 hash. It took them 6610 years of processor time to do it. 

We use [java.security.MessageDigest](https://docs.oracle.com/javase/6/docs/api/java/security/MessageDigest.html) class and SHA-1 Algorithm in this example.

### Step 2: Parse File(s)

As mentioned earlier, we use java.security.MessageDigest class and SHA-1 Algorithm in this example. Message digests are secure one-way hash functions that take arbitrary-sized data and output a fixed-length hash value.

The code creates a new file input stream for reading the file content. The data is processed using the update method call. Once all the data has been updated, the digest methods should be called to complete the hash computation.

See the code:
%[https://gist.github.com/Igvir/6652eaf6c1e5832e2ae4d3d1d12146a0]


### Step 3: Generate the UID

The digest method returns an array of bytes in decimal format. We need to convert it to hexadecimal format before call toString() method to finally to get an UID based on file data. 

%[https://gist.github.com/Igvir/d8af1d8a59c2e678790fa5f7067c9691]

If the file changes in any way the resultant hash code will be different. You can handle those differences according to the context as a new version or as an integrity check failure. 

### Complete Sample Code

The complete code can be found bellow:

%[https://gist.github.com/Igvir/2a1a94e7c348049de110d8d2fa5bb94b]

## Conclusions

We have made a tour of the concepts associated with the generation of Hash codes and their applications for the generation of Unique identifiers for objects in our application domain.
The main strength of Hashing algorithms is that they offer, at the same time, the possibility of generating an integrity verification code and an identifier reliable enough to be the unique identifier. Hashing libraries are widely available as standard in practically all programming languages, so their use is very feasible and quick to implement.

Although in the example we have used the case of generating filenames, many other uses of Hashing algorithms are well known. Just to name a few we can mention:

- Database indexing
- Password storage
- Data compression
- Search algorithms
- Cryptography
  

## Credits

  - Cover photo by [George Prentzas](https://unsplash.com/@georgeprentzas?utm_source=unsplash&utm_medium=referral&utm_content=creditCopyText) on  [Unsplash](https://unsplash.com/photos/SRFG7iwktDk?utm_source=unsplash&utm_medium=referral&utm_content=creditCopyText)
  - Gupta, L. (2022, January 25). Java File Checksum. Howtodoinjava. Retrieved February 18, 2033, from https://howtodoinjava.com/java/java-security/sha-md5-file-checksum-hash/
  
  