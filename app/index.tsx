import { StyleSheet, Text, View } from 'react-native'
import React from 'react'
import Login from '@/app/screens/Login'

const index: React.FC =  () => {
  return (
    <View style={styles.container}>
      <Login></Login>
    </View>
  )
}

export default index

const styles = StyleSheet.create({
  container: {
    flex: 1
  },
})